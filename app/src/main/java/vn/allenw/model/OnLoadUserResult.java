package vn.allenw.model;

import java.util.List;

/**
 * Created by Allen on 01-Apr-16.
 */
public interface OnLoadUserResult {
    public void onLoadUserSuccess(List<User> userList);

    public void onLoadUserError();
}
