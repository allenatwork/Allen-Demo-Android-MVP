package vn.allenw.view;

import java.util.List;

import vn.allenw.model.User;

/**
 * Created by Allen on 01-Apr-16.
 */
public interface ListUserView {
    void reload();

    void showNoData();

    void showError();

    void displayListuser(List<User> listUser);

}
