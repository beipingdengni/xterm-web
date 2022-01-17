package xyz.beipingdenigni.xtermweb.service;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import javax.swing.*;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.service
 * @description: MyUserInfo
 * @author: tianbeiping1
 * @Date: 2022/1/15 4:52 下午
 **/
public class MyUserInfo implements UserInfo, UIKeyboardInteractive {
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean promptYesNo(String message) {
        Object[] options = {"yes", "no"};
        int foo = JOptionPane.showOptionDialog(null,
                message,
                "Warning",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null, options, options[0]);
        return foo == 0;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return false;
    }

    @Override
    public boolean promptPassword(String message) {
        return false;
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public String[] promptKeyboardInteractive(String destination,
                                              String name,
                                              String instruction,
                                              String[] prompt,
                                              boolean[] echo) {
        return null;
    }
}
