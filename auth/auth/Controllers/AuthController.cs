using auth.Data;
using auth.DataObject;
using auth.Models;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace auth.Controllers
{
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {
        private readonly AuthContext context;
        private readonly ILogger<AuthController> _logger;

        public AuthController(AuthContext context, ILogger<AuthController> logger)
        {
            this.context = context;
            _logger = logger;
        }

        [HttpPost("register")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public IActionResult Register([Required] RegisterData data)
        {
            try
            {
                context.RegisterUser(data);
            }
            catch (UserAlreadyExistsException)
            {
                return Conflict();
            }

            try
            {
                return Ok(new ValidToken { value = context.CreateToken(new LoginData { Login = data.Login, Password = data.Password }) });
            }
            catch (BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpGet("token")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public IActionResult Login([Required] LoginData data)
        {
            try
            {
                return Ok(new ValidToken { value = context.CreateToken(data) });
            }
            catch(BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("logout")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public IActionResult Logout()
        {
            return Ok();
        }
    }
}
