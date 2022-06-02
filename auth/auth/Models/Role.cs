namespace auth.Models
{
    using System.ComponentModel.DataAnnotations.Schema;

    public class Role
    {
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        public int RoleID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public ICollection<RolePossesion> RolePossesions { get; set; }
    }
}
