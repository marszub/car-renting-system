#ifndef AUTH_ICE
#define AUTH_ICE

module Auth
{
    enum Role { User, Admin };

    interface Account
    {
        bool verifyRole(Role role);
    };
};
#endif
