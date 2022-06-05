namespace auth.Models
{
    public class User
    {
        public int UserID { get; set; }
        public string Login { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }

        public ICollection<RolePossesion> RolePossesions { get; set; }
    }
}
