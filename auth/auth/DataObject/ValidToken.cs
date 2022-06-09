using System.ComponentModel.DataAnnotations;

namespace auth.DataObject
{
    public class ValidToken
    {
        [Required]
        public string token { get; set; } = default!;
    }
}
