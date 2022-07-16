using Auth.Models;
using System.ComponentModel.DataAnnotations;

namespace Auth.DataObject
{
    public class UserData
    {
        [Required]
        [StringLength(50, MinimumLength = 4)]
        public string Login { get; set; } = default!;

        [Required]
        public string Role { get; set; } = default!;

        public UserData(User user)
        {
            Login = user.Login;
            Role = user.Role.Name;
        }
    }
}
