// medicos-page.component.ts

import { Component, OnInit } from '@angular/core';
import { MedicoService } from '../services/medico.service';
import { Medico } from '../models/medico.interface';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-medicos-page',
  template: `
    <h2>Gestión de Médicos (MediSystem)</h2>
    <ul>
      <li *ngFor="let medico of medicos$ | async">
        {{ medico.nombreMedico }} - {{ medico.nombreEspecialidad }}
      </li>
    </ul>
  `,
})
export class MedicosPageComponent implements OnInit {
  // Usamos un Observable para manejar la lista de médicos
  medicos$!: Observable<Medico[]>;

  // Inyectamos solo el Servicio de Negocio
  constructor(private medicoService: MedicoService) {}

  ngOnInit(): void {
    // El componente le pide al servicio los datos ya procesados/listos
    this.medicos$ = this.medicoService.getMedicosParaUI();
  }
}
