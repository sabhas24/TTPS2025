import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HomeFooter } from "../home/home-footer/home-footer";

@Component({
    selector: 'app-forgot-password',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, RouterLink, HomeFooter],
    templateUrl: './forgot-password.html',
    styleUrl: './forgot-password.css'
})
export class ForgotPassword {
    form: FormGroup;
    isSubmitting = false;
    message = '';
    error = '';

    constructor(private fb: FormBuilder, private authService: AuthService) {
        this.form = this.fb.group({
            email: ['', [Validators.required, Validators.email]]
        });
    }

    onSubmit() {
        if (this.form.invalid) return;

        this.isSubmitting = true;
        this.message = '';
        this.error = '';

        this.authService.forgotPassword(this.form.value.email).subscribe({
            next: (res: any) => {
                this.message = res.message;
                this.isSubmitting = false;
            },
            error: (err) => {
                this.error = typeof err.error === 'string' ? err.error : (err.error?.message || 'Ocurrió un error al procesar la solicitud.');
                this.isSubmitting = false;
            }
        });
    }
}
