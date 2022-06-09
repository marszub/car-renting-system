namespace auth.Models
{
    public class User
    {
        public int UserID { get; set; } = default!;
        public string Login { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public int RoleID { get; set; }
        public virtual Role Role { get; set; } = default!;
        public virtual ICollection<Token> Tokens { get; set; } = default!;

        public User(string login, string email, string password)
        {
            Login = login;
            Email = email;
            Password = password;
        }
    }
}
