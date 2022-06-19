export const USERID_KEY = "userID";

export const userIdStorage = {
    _userId: null,

    get userId() {
        return localStorage.getItem(USERID_KEY);
    },

    set userId(id) {
        localStorage.setItem(USERID_KEY, id);
    },

    revokeUserId() {
        localStorage.removeItem(USERID_KEY);
    }
}
