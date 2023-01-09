package com.fariseducation;

import com.fariseducation.Data.Guardian;
import com.fariseducation.Data.ObservedData.DataManager;
import com.fariseducation.UIBase.UIComponent;
import com.fariseducation.UIBase.UIFrameConstraints;
import com.fariseducation.UIBase.UIGroup;
import com.fariseducation.UIBase.UIListBuilder;
import com.fariseducation.UIBase.UIScrollContainer;
import com.fariseducation.UIBase.UISpacer;
import com.fariseducation.UIBase.UIWindow;
import com.fariseducation.UIBase.UIEnums.UIAlignment;
import com.fariseducation.UIBase.UIEnums.UIAxis;
import com.fariseducation.UIBase.UITextElements.UIButton;
import com.fariseducation.UIBase.UITextElements.UILabel;

public class GuardianListWindow {
    private UIWindow window;

    public GuardianListWindow() {
        spawn();
    }

    private void spawn() {
        this.window = (UIWindow)new UIWindow("View Guardians", new UIFrameConstraints(400, 500), new UIComponent[]{
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(3),
                new UILabel("Guardians")
                    .format(true, false, 2),
                new UISpacer()
            }),
            new UIScrollContainer(
                new UIListBuilder<Guardian>(
                    DataManager.getInstance().getGuardians(), 
                    (Guardian guardian) -> {
                        return new UIGroup(UIAxis.HORIZONTAL, UIAlignment.NONE, new UIComponent[] {
                            new UILabel(guardian.getName()),
                            new UISpacer(),
                            new UIButton("Edit")
                                .onPress(() -> {
                                    new GuardianEditorWindow(guardian);
                                })
                        });
                    }, 
                    UIAxis.VERTICAL)
            )
                .setPreferredSize(5000, 5000),
            new UIGroup(UIAxis.HORIZONTAL, new UIComponent[]{
                new UISpacer(),
                new UIButton("+")
                    .onPress(() -> {
                        new GuardianEditorWindow();
                    }),
                new UISpacer()
            }),
        })
            .setMinSize(300, 300);
    }
}
