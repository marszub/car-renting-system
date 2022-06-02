namespace auth.Models
{
    public class RolePossesion
    {
        public int RolePossesionID { get; set; }
        public User User { get; set; }
        public Role Role { get; set; }
    }
}
