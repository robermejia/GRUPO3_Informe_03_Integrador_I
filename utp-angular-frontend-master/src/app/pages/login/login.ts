import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  form = new FormGroup({
    email: new FormControl('admin@tienda.com', [
      Validators.required,
      Validators.email,
    ]),
    password: new FormControl('123456', [Validators.required]),
  });

  constructor(private authService: AuthService, private router: Router) {}

  loginClick() {
    let email: string = this.form.value.email || '';
    let password: string = this.form.value.password || '';

    this.authService.login(email, password).subscribe({
      next: (res) => {
        this.authService.loginExitoso(res.data);
        if ([1, 2].includes(res.data.user.id_rol)) {
          this.router.navigate(['/admin/productos']);
        } else {
          this.router.navigate(['/productos']);
        }
      },
      error: (ex) => {
        if (ex instanceof HttpErrorResponse) {
          if (!!ex.error.message) {
            // window.alert(ex.error.message);
          }
        }
      },
    });
  }
}
