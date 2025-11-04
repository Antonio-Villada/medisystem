import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'app-login',
  // Apunta a los archivos separados
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string | null = null;

  constructor(private authService: AuthService) {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.errorMessage = null;

      // Log para diagnosticar el objeto que se envía (solo para desarrollo)
      console.log('Enviando credenciales:', this.loginForm.value);

      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          // Si el login es exitoso, la redirección ocurre en el AuthService.
          console.log('Login exitoso. Redirigiendo...');
        },
        error: (err) => {
          // Manejo de error específico (ej: 401 Unauthorized)
          this.errorMessage =
            'Credenciales inválidas. Por favor, verifica tu usuario y contraseña.';
          console.error('Error de Login:', err);
        },
      });
    }
  }
}
