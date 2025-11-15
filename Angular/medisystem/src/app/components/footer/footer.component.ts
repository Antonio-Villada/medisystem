import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario para *ngFor

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule], // Importamos CommonModule
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent {
  // Obtenemos el año actual para el copyright
  currentYear = new Date().getFullYear();

  // La información que solicitaste
  appInfo = {
    name: 'MediSystem',
    description: 'Aplicacion para gestion de citas medicas',
    version: '1.0.0',
    integrantes: [
      'Jesus Antonio Villada Morales',
      'Ivan Ramiro Bañol Motato',
      'Cristian Camilo Castaño',
    ],
  };

  constructor() {}
}
