namespace auth.Models
{
    public class User
    {
        public int UserID { get; set; }
        public string Login { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public virtual Role Role { get; set; }

        public User(string login, string email, string password, Role role)
        {
            Login = login;
            Email = email;
            Password = password;
            Role = role;
        }
    }
}
