namespace auth.DataObject
{
    using System.ComponentModel.DataAnnotations;
    public class RegisterData
    {
        [Required]
        [StringLength(50, MinimumLength = 4)]
        public string Login { get; set; } = default!;

        [Required]
        [EmailAddress]
        public string Email { get; set; } = default!;

        [Required]
        [StringLength(200, MinimumLength = 8)]
        public string Password { get; set; } = default!;
    }
}
