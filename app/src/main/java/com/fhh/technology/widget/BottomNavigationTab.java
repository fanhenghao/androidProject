package com.fhh.technology.widget;

import androidx.fragment.app.Fragment;

import com.fhh.technology.R;
import com.fhh.technology.module.discover.DiscoverFragment;
import com.fhh.technology.module.home.HomeFragment;
import com.fhh.technology.module.person.PersonFragment;
import com.fhh.technology.module.tool.ToolFragment;
import com.fhh.technology.module.wait.WaitFragment;

/**
 * 底部导航Tab
 */

public enum BottomNavigationTab {

    TAB_HOME(0, HomeFragment.class, R.string.tab_bottom_home, R.drawable.select_btn_home),
    TAB_DISCOVER(1, DiscoverFragment.class, R.string.tab_bottom_discover, R.drawable.select_btn_discover),
    TAB_TOOL(2, ToolFragment.class, R.string.tab_bottom_tool, R.drawable.select_btn_tool),
    TAB_WAIT(3, WaitFragment.class, R.string.tab_bottom_wait, R.drawable.select_btn_wait),
    TAB_PERSON(4, PersonFragment.class, R.string.tab_bottom_person, R.drawable.select_btn_person),;

    public final int tabIndex;


    public final Class<? extends Fragment> clazz;

    public final int resId;

    public final int fragmentId;

    public final int iconId;


    BottomNavigationTab(int index, Class<? extends Fragment> clazz, int resId, int iconId) {
        this.tabIndex = index;
        this.clazz = clazz;
        this.resId = resId;
        this.iconId = iconId;
        this.fragmentId = index;
    }

    public static final BottomNavigationTab fromTabIndex(int tabIndex) {
        for (BottomNavigationTab value : BottomNavigationTab.values()) {
            if (value.tabIndex == tabIndex) {
                return value;
            }
        }

        return null;
    }

}
