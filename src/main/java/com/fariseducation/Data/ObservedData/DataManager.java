package com.fariseducation.Data.ObservedData;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.GuardianshipRelationship;
import com.fariseducation.Data.ManagedData;
import com.fariseducation.Data.ManagedDataSource;
import com.fariseducation.Data.Session;
import com.fariseducation.Data.Student;
import com.fariseducation.Data.TimeGroup;
import com.fariseducation.Data.Tutor;
import com.fariseducation.Data.Indexing.TreeNode;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DataManager {
    private static DataManager instance = null;

    private ObservedUnlockedList<Guardian> guardians;
    private ObservedUnlockedList<Student> students;
    private ObservedUnlockedList<Tutor> tutors;
    private ObservedUnlockedList<Session> sessions;
    private ObservedUnlockedList<GuardianshipRelationship> guardianshipRelationships;
    private ObservedUnlockedList<TimeGroup> timeGroups;
    //private SpellingDictionary spellDict;
    private HashMap<UUID,ManagedData> dataLookup;
    //private HashMap<String,TreeNode> indexedTrees;

    private transient ArrayList<WeakReference<ObservedLiveList>> liveLists = 
        new ArrayList<WeakReference<ObservedLiveList>>();

    private DataManager() {
        this.guardians = new ObservedUnlockedList<Guardian>();
        this.students = new ObservedUnlockedList<Student>();
        this.tutors = new ObservedUnlockedList<Tutor>();
        this.sessions = new ObservedUnlockedList<Session>();
        this.guardianshipRelationships = new ObservedUnlockedList<GuardianshipRelationship>();
        this.timeGroups = new ObservedUnlockedList<TimeGroup>();

        this.dataLookup = new HashMap<UUID,ManagedData>();
    }

    public static DataManager getInstance() {
        if(instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    public void saveAllData() {
        for(ManagedData datum : this.dataLookup.values()) {
            datum.save();
        }
    }
    public ManagedData getByUUID(UUID id) {
        return this.dataLookup.get(id);
    } 
    public void registerDatum(ManagedDataSource datum) {
        this.dataLookup.put(datum.getUUID(), datum);
        
        if(!datumIsRegistered(datum)) {
            if(datum instanceof Guardian) guardians.add((Guardian)datum);
            if(datum instanceof Student) students.add((Student)datum);
            if(datum instanceof Tutor) tutors.add((Tutor)datum);
            if(datum instanceof Session) sessions.add((Session)datum);
            if(datum instanceof GuardianshipRelationship) guardianshipRelationships.add((GuardianshipRelationship)datum);
            if(datum instanceof TimeGroup) timeGroups.add((TimeGroup)datum);
        }

        for(WeakReference<ObservedLiveList> listRef : this.liveLists) {
            System.out.println("Testing");
            if(listRef.get() != null) System.out.println(listRef.get().isMember(datum));
            if(listRef.get() != null && listRef.get().isMember(datum)) listRef.get().addMember(datum);;
        }
    }
    public void deleteDatum(ManagedDataSource datum) {
        this.dataLookup.remove(datum.getUUID());

        if(datum instanceof Guardian) guardians.remove((Guardian)datum);
        if(datum instanceof Student) students.remove((Student)datum);
        if(datum instanceof Tutor) tutors.remove((Tutor)datum);
        if(datum instanceof Session) sessions.remove((Session)datum);
        if(datum instanceof GuardianshipRelationship) guardianshipRelationships.remove((GuardianshipRelationship)datum);
        if(datum instanceof TimeGroup) timeGroups.remove((TimeGroup)datum);

        for(WeakReference<ObservedLiveList> listRef : this.liveLists) {
            if(listRef.get() != null && listRef.get().isMember(datum)) listRef.get().removeMember(datum);;
        }
    }
    public boolean datumIsRegistered(ManagedDataSource datum) {
        return
            (datum instanceof Guardian && guardians.contains((Guardian)datum)) ||
            (datum instanceof Student && students.contains((Student)datum)) ||
            (datum instanceof Tutor && tutors.contains((Tutor)datum)) ||
            (datum instanceof Session && sessions.contains((Session)datum)) ||
            (datum instanceof GuardianshipRelationship && 
                guardianshipRelationships.contains((GuardianshipRelationship)datum)) ||
            (datum instanceof TimeGroup && timeGroups.contains((TimeGroup)datum));
    }
    public void registerLiveList(ObservedLiveList liveList) {
        this.liveLists.add(new WeakReference(liveList));
    }
    public void printRegisteredLiveLists() {
        for(WeakReference<ObservedLiveList> liveList : this.liveLists) {
            System.out.println(liveList.get());
        }
    }

    public ObservedLockedList<Guardian> getGuardians() {
        return (ObservedLockedList<Guardian>)this.guardians;
    }
    public ObservedLockedList<Student> getStudents() {
        return (ObservedLockedList<Student>)this.students;
    }
    public ObservedLockedList<Tutor> getTutors() {
        return (ObservedLockedList<Tutor>)this.tutors;
    }
    public ObservedLockedList<Session> getSessions() {
        return (ObservedLockedList<Session>)this.sessions;
    }
    public ObservedLockedList<GuardianshipRelationship> getGuardianshipRelationships() {
        return (ObservedLockedList<GuardianshipRelationship>)this.guardianshipRelationships;
    }
    public ObservedLockedList<TimeGroup> getTimeGroups() {
        return (ObservedLockedList<TimeGroup>)this.timeGroups;
    }

    public ObservedLiveList<Session> getSessionsForStudent(ObservedGeneric<Student> student) {
        return new ObservedLiveList<Session>(
            new ObservedDatum[]{student},
            DataManager.getInstance()::getSessions, 
            (Session session) -> {
                return student.getVal()==session.getStudent().getVal();
            });
    }
    public ObservedLiveList<Guardian> getGuardiansForStudent(ObservedGeneric<Student> student) {
        return new ObservedLiveList<Guardian>(
            new ObservedDatum[]{student},
            DataManager.getInstance()::getGuardians, 
            (Guardian guardian) -> {
                for(GuardianshipRelationship gr : DataManager.getInstance().getGuardianshipRelationships()) {
                    if(gr.getGuardian().getVal()==guardian && gr.getStudent()==student) return true;
                }
                return false;
            });
    }
    public ObservedLiveList<GuardianshipRelationship> getGuardianshipRealtionshipsForStudent(ObservedGeneric<Student> student) {
        return new ObservedLiveList<GuardianshipRelationship>(
            new ObservedDatum[]{student},
            DataManager.getInstance()::getGuardianshipRelationships, 
            (GuardianshipRelationship gr) -> {
                return student.getVal()==gr.getStudent().getVal();
            });
    }
    public ObservedLiveList<Tutor> getTutorsForStudent(ObservedGeneric<Student> student) {
        return new ObservedLiveList<Tutor>(
            new ObservedDatum[]{student},
            DataManager.getInstance()::getTutors, 
            (Tutor tutor) -> {
                for(Session session : DataManager.getInstance().getSessions()) {
                    if(tutor==session.getTutor().getVal() && student.getVal()==session.getStudent().getVal()) return true;
                }
                return false;
            });
    }
    public ObservedLiveList<Tutor> getTutorsForTimeGroup(
        ObservedGeneric<TimeGroup> timeGroup, 
        ObservedGeneric<Student> student) 
    {
        return new ObservedLiveList<Tutor>(
            new ObservedDatum[]{timeGroup, student},
            DataManager.getInstance()::getTutors, 
            (Tutor tutor) -> {
                for(Session session : DataManager.getInstance().getSessions()) {
                    if(
                        tutor==session.getTutor().getVal() && 
                        student.getVal()==session.getStudent().getVal() && 
                        timeGroup.getVal().containsSession(session)) return true;
                }
                return false;
            });
    }

    public ObservedLiveList<Session> getSessionsInTimeGroup(ObservedGeneric<TimeGroup> timeGroup) {
        return new ObservedLiveList<Session>(
            new ObservedDatum[]{timeGroup},
            DataManager.getInstance()::getSessions, 
            (Session session) -> {
                return timeGroup.getVal().containsSession(session);
            });
    }
    public ObservedLiveList<Student> getStudentsForTimeGroup(ObservedGeneric<TimeGroup> timeGroup) {
        return new ObservedLiveList<Student>(
            new ObservedDatum[]{timeGroup},
            DataManager.getInstance()::getStudents, 
            (Student student) -> {
                for(Session session : DataManager.getInstance().getSessions()) {
                    if(student==session.getStudent().getVal() && timeGroup.getVal().containsSession(session)) return true;
                }
                return false;
            });
    }

    public ObservedLiveList<Student> getStudentsForGuardian(ObservedGeneric<Guardian> guardian) {
        return new ObservedLiveList<Student>(
            new ObservedDatum[]{guardian},
            DataManager.getInstance()::getStudents, 
            (Student student) -> {
                for(GuardianshipRelationship gr : DataManager.getInstance().getGuardianshipRelationships()) {
                    if(gr.getStudent().getVal()==student && gr.getGuardian()==guardian) return true;
                }
                return false;
            });
    }
    public ObservedLiveList<GuardianshipRelationship> getGuardianshipRealtionshipsForGuardian(ObservedGeneric<Guardian> guardian) {
        System.out.println("GGRFG Fetched");
        return new ObservedLiveList<GuardianshipRelationship>(
            new ObservedDatum[]{guardian},
            DataManager.getInstance()::getGuardianshipRelationships, 
            (GuardianshipRelationship gr) -> {
                System.out.println("GGRFG:");
                System.out.println(guardian);
                System.out.println(guardian.getVal().getName().getVal());
                System.out.println(gr);
                System.out.println(gr.getGuardian());
                System.out.println(gr.getGuardian().getVal().getName().getVal());
                return guardian.getVal()==gr.getGuardian().getVal();
            });
    }
}
