using auth.Data;
using auth.DataObject;
using auth.Models;
using auth.Service;
using Microsoft.AspNetCore.Mvc;
using System.ComponentModel.DataAnnotations;

namespace auth.Controller
{
    [ApiController]
    [Route("api/auth")]
    public class AuthController : ControllerBase
    {
        private readonly AuthContext context;
        private readonly TokenService tokenService;
        private readonly ILogger<AuthController> _logger;

        public AuthController(AuthContext context, ILogger<AuthController> logger)
        {
            this.context = context;
            this.tokenService = new TokenService(context);
            _logger = logger;
        }

        [HttpPost("register")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status409Conflict)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public ActionResult<ValidToken> Register([Required] RegisterData data)
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
                return Ok(new ValidToken { token = tokenService.CreateToken(new LoginData { Login = data.Login, Password = data.Password }) });
            }
            catch (BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("token")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public ActionResult<ValidToken> Login([Required] LoginData data)
        {
            try
            {
                return Ok(new ValidToken { token = tokenService.CreateToken(data) });
            }
            catch(BadUserCredentialsException)
            {
                return Unauthorized();
            }
        }

        [HttpPost("logout")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status401Unauthorized)]
        public IActionResult Logout([FromHeader] string token)
        {
            try
            {
                tokenService.DeleteToken(token);
                return NoContent();
            }
            catch(UnauthorizedAccessException)
            {
                return Unauthorized();
            }
        }
    }
}
