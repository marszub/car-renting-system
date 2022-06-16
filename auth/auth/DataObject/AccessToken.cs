using System.ComponentModel.DataAnnotations;

namespace auth.DataObject
{
    public class AccessToken
    {
        [Required]
        public string token { get; set; } = default!;
    }
}
