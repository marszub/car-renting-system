namespace auth.dto
{
    using System.ComponentModel.DataAnnotations;
    public class RegisterData
    {
        [Required]
        [StringLength(50, MinimumLength = 4)]
        public string Login { get; set; }

        [Required]
        [EmailAddress]
        public string Mail { get; set; }

        [Required]
        [StringLength(200, MinimumLength = 8)]
        public string Password { get; set; }
    }
}
