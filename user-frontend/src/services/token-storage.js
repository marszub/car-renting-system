export const ACCESS_TOKEN_KEY = "accessToken";

export const tokenStorage = {
    _accessToken: null,

    get accessToken() {
        return localStorage.getItem(ACCESS_TOKEN_KEY);
    },

    set accessToken(token) {
        localStorage.setItem(ACCESS_TOKEN_KEY, token);
    },

    revokeToken() {
        localStorage.removeItem(ACCESS_TOKEN_KEY);
    }
}
