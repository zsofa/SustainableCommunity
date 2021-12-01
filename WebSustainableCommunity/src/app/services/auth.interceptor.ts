import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpContextToken } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

export const IS_TOKEN_ENABLED = new HttpContextToken<boolean>(() => true);

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        if (req.context.get(IS_TOKEN_ENABLED)) {
            const idToken = localStorage.getItem("jwtToken") || sessionStorage.getItem("jwtToken");

            if (idToken) {
                const cloned = req.clone({
                    headers: req.headers.set("Authorization",
                        "Bearer " + idToken)
                });
    
                return next.handle(cloned);
            }
        }

        return next.handle(req);
    }
}