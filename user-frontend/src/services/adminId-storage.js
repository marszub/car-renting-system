export const USERID_KEY = "adminID";

export const adminIdStorage = {
    _adminId: null,

    get adminId() {
        return localStorage.getItem(USERID_KEY);
    },

    set adminId(id) {
        localStorage.setItem(USERID_KEY, id);
    },

    revokeAdminId() {
        localStorage.removeItem(USERID_KEY);
    }
}
