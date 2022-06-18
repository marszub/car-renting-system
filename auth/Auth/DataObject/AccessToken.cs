using System.ComponentModel.DataAnnotations;

namespace Auth.DataObject
{
    public class AccessToken
    {
        [Required]
        public string token { get; set; } = default!;
    }
}
