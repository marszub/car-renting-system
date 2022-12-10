export const HTTP_OK = 200;
export const HTTP_CREATED = 201;
export const HTTP_NO_CONTENT = 204;
export const HTTP_BAD_REQUEST = 400;
export const HTTP_UNAUTHORIZED = 401;
export const HTTP_NOT_FOUND = 404;
export const HTTP_COLISION = 409;
export const HTTP_TEMPORARY_REDIRECT = 307;

export const SERVER_ERROR = 500;

export const isServerError = status => {
    return status >= 500 && status <= 599;
  }
