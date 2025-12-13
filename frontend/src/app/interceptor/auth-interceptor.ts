import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  if (token) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Baerrer ${token}`
      }
    })
  }
  return next(req);
};
