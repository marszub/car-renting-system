#ifndef AUTH_ICE
#define AUTH_ICE

module Auth
{
    enum Role { User };
    enum TokenVerificationStatus { Ok, InvalidToken, RoleNotAssigned};

    struct AccessData {
        string token;
        Role role;
    };

    interface Account
    {
        TokenVerificationStatus verifyToken(AccessData accessData);
    };
};
#endif
