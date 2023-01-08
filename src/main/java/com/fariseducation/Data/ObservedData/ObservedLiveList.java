package com.fariseducation.Data.ObservedData;

import java.util.function.Function;
import java.util.function.Supplier;

public class ObservedLiveList<ContentType> extends ObservedLockedList<ContentType> implements DataObserver {
    private Function<ContentType,Boolean> memberTest;
    private Supplier<ObservedLockedList<ContentType>> source;

    public ObservedLiveList(
        Supplier<ObservedLockedList<ContentType>> source,
        Function<ContentType,Boolean> memberTest) 
    {
        this.memberTest = memberTest;
        this.source = source;

        build();
        DataManager.getInstance().registerLiveList(this);
    }
    public ObservedLiveList(
        ObservedDatum[] observedVariables,
        Supplier<ObservedLockedList<ContentType>> source,
        Function<ContentType,Boolean> memberTest)
    {
        this(source, memberTest);

        for(ObservedDatum od : observedVariables) {
            od.addObserver(this);
        }
    }

    public void build() {
        this.contents.clear();

        for(ContentType content : source.get()) {
            if(memberTest.apply(content)) this.contents.add(content);
        }
    }
    @SuppressWarnings("unchecked")
    public boolean isMember(Object o) {
        System.out.println("Member check");
        
        try {
            System.out.println(this.memberTest.apply((ContentType)o));
            return this.memberTest.apply((ContentType)o);
        }
        catch(ClassCastException e) {
            return false;
        }
    }

    protected void addMember(ContentType member) {
        if(!isMember(member)) this.contents.add(member);
    }
    protected void removeMember(ContentType member) {
        if(isMember(member)) this.contents.remove(member);
    }

    @Override
    public void updateAfterDataChange() {
        build();
    }
}
