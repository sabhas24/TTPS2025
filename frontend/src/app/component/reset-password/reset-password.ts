import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth-service';
import { HomeFooter } from "../home/home-footer/home-footer";

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, HomeFooter],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css',
})
export class ResetPassword {
  form: FormGroup;
  token: string | null = null;
  error: string = '';
  success: string = '';
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    this.form = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirm: ['', [Validators.required]]
    }, { validators: this.passwordsMatch });
  }

  ngOnInit() {
    this.token = this.route.snapshot.queryParamMap.get('token');
  }

  passwordsMatch(group: FormGroup) {
    return group.get('password')!.value === group.get('confirm')!.value
      ? null : { notMatch: true };
  }

  onSubmit() {
    if (this.form.invalid || !this.token) return;
    this.isSubmitting = true;
    this.authService.resetPassword(this.token, this.form.value.password).subscribe({
      next: () => {
        this.success = '¡Contraseña restablecida con éxito!';
        this.isSubmitting = false;
        setTimeout(() => this.router.navigate(['/login']), 1000);
      },
      error: (err) => {
        this.error = err.error?.message || 'Error al restablecer la contraseña';
        this.isSubmitting = false;
      }
    });
  }
}
