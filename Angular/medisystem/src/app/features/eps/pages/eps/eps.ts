// eps.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Necesario para directivas como *ngFor, *ngIf
import { FormsModule } from '@angular/forms'; // Necesario para [(ngModel)]
import { Eps } from '../../models/eps.interface'; // Importamos la interfaz Eps
import { EpsService } from '../../service/eps.service'; // Importamos el servicio EpsService

@Component({
  selector: 'app-eps',
  // Si usas Angular Standalone Components, debes importar CommonModule y FormsModule
  standalone: true, // Asumo que es un componente standalone por tu archivo original
  imports: [CommonModule, FormsModule],
  templateUrl: './eps.html',
  styleUrl: './eps.css',
  // Note: La clase ya no se llama 'Eps', sino 'EpsComponent' por convención
})
export class EpsComponent implements OnInit {
  // Arreglo para almacenar la lista de EPS
  public epsList: Eps[] = [];
  // Objeto para manejar el formulario de nueva/edición
  public currentEps: Eps = { nombreEps: '' };
  // Bandera para indicar si estamos editando o creando
  public isEditing: boolean = false;
  // Mensaje de estado
  public message: string | null = null;

  // 1. Inyección de dependencias: Inyectamos el servicio
  constructor(private epsService: EpsService) {}

  // 2. Ciclo de vida: Cargamos la lista al iniciar
  ngOnInit(): void {
    this.loadEpsList();
  }

  // --- MÉTODOS CRUD ---

  // READ (Cargar todas las EPS)
  loadEpsList(): void {
    this.epsService.getAll().subscribe({
      next: (data) => {
        this.epsList = data;
        this.message = null;
      },
      error: (err) => {
        console.error('Error al cargar las EPS', err);
        this.message = 'Error al cargar la lista de EPS.';
      }
    });
  }

  // CREATE (Preparar para crear)
  prepareCreate(): void {
    this.currentEps = { nombreEps: '' }; // Objeto vacío para el formulario
    this.isEditing = false;
    this.message = null;
  }

  // UPDATE (Preparar para editar)
  prepareEdit(eps: Eps): void {
    // Copia profunda del objeto para no modificar la lista directamente
    this.currentEps = { ...eps };
    this.isEditing = true;
    this.message = null;
  }

  // SAVE (Crear o Actualizar)
  saveEps(): void {
    if (this.isEditing && this.currentEps.idEps) {
      // Si estamos editando y tenemos un ID, llamamos a update
      this.epsService.update(this.currentEps.idEps, this.currentEps).subscribe({
        next: () => {
          this.message = `EPS ${this.currentEps.nombreEps} actualizada con éxito.`;
          this.loadEpsList(); // Recargar la lista
          this.prepareCreate(); // Limpiar el formulario
        },
        error: (err) => {
          console.error('Error al actualizar la EPS', err);
          this.message = 'Error al actualizar la EPS.';
        }
      });
    } else {
      // Si no estamos editando o no hay ID, llamamos a create
      // Aseguramos que no enviamos el idEps, ya que es opcional y lo genera el backend
      const newEps: Eps = { nombreEps: this.currentEps.nombreEps };
      this.epsService.create(newEps).subscribe({
        next: () => {
          this.message = `EPS ${this.currentEps.nombreEps} creada con éxito.`;
          this.loadEpsList();
          this.prepareCreate();
        },
        error: (err) => {
          console.error('Error al crear la EPS', err);
          this.message = 'Error al crear la EPS.';
        }
      });
    }
  }

  // DELETE (Eliminar)
  deleteEps(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar esta EPS?')) {
      this.epsService.delete(id).subscribe({
        next: () => {
          this.message = 'EPS eliminada con éxito.';
          this.loadEpsList();
          this.prepareCreate();
        },
        error: (err) => {
          console.error('Error al eliminar la EPS', err);
          this.message = 'Error al eliminar la EPS.';
        }
      });
    }
  }
}
