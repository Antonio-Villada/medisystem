import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { Eps } from '../../models/eps.interface'; 
import { EpsService } from '../../service/eps.service'; 

@Component({
  selector: 'app-eps',
  standalone: true, 
  imports: [CommonModule, FormsModule], 
  templateUrl: './eps.html', 
  styleUrl: './eps.css'
})
export class EpsComponent implements OnInit { 

  private epsService = inject(EpsService); 

  // --- Propiedades para el CRUD ---

  // Leer (Read)
  public epsList: Eps[] = [];
  public loading: boolean = true; 

  // Crear/Editar (Create/Update)
  // FIX 2: Renombrado de 'epsForm' a 'currentEps' para coincidir con la plantilla HTML.
  public currentEps: Partial<Eps> = { nombreEps: '' }; 
  public isEditing: boolean = false; 

  // FIX 1: Consolidado de 'errorMessage' y 'successMessage' en la única propiedad 'message' que usa el HTML.
  public message: string | null = null;
  // NOTE: Se eliminan 'errorMessage' y 'successMessage' explícitos.

  // --- Inicialización ---

  ngOnInit(): void {
    this.loadEps();
  }

  // ----------------------------------------------------
  // --- 1. Leer (Read) - Obtener todos -----------------
  // ----------------------------------------------------

  loadEps(): void {
    this.loading = true;
    this.clearMessages();
    
    this.epsService.getAll().subscribe({
      next: (data) => {
        this.epsList = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar EPS:', error);
        // Uso de la nueva propiedad 'message'
        this.message = 'No se pudieron cargar las EPS. Inténtelo de nuevo.';
        this.loading = false;
      }
    });
  }
  
  // ----------------------------------------------------
  // --- 2. Crear y Actualizar (Create & Update) --------
  // ----------------------------------------------------

  /**
   * FIX 4: Renombrado de 'editEps' a 'prepareEdit' para coincidir con el HTML.
   * Prepara el formulario para editar una EPS.
   * @param eps El objeto EPS a editar.
   */
  prepareEdit(eps: Eps): void {
    this.isEditing = true;
    this.clearMessages();
    // Uso de la propiedad 'currentEps'
    this.currentEps = { ...eps }; 
  }

  /**
   * Lógica para guardar (Crear o Actualizar) una EPS.
   */
  saveEps(): void {
    // 1. Validación básica
    if (!this.currentEps.nombreEps || this.currentEps.nombreEps.trim() === '') {
      this.message = 'El nombre de la EPS es obligatorio.';
      return;
    }

    this.loading = true;
    this.clearMessages();

    // Uso de la propiedad 'currentEps'
    if (this.isEditing && this.currentEps.idEps) {
      // **Actualizar (Update)**
      this.epsService.update(this.currentEps.idEps, this.currentEps as Eps).subscribe({
        next: (updatedEps) => {
          this.message = `EPS "${updatedEps.nombreEps}" actualizada correctamente.`;
          this.loadEps(); 
          this.prepareCreate(); // Llamada al método renombrado
        },
        error: (error) => {
          console.error('Error al actualizar:', error);
          this.message = 'Error al actualizar la EPS.';
          this.loading = false;
        }
      });
    } else {
      // **Crear (Create)**
      const newEps: Partial<Eps> = {
        nombreEps: this.currentEps.nombreEps // Uso de la propiedad 'currentEps'
      };

      this.epsService.create(newEps as Eps).subscribe({
        next: (createdEps) => {
          this.message = `EPS "${createdEps.nombreEps}" creada correctamente.`;
          this.loadEps();
          this.prepareCreate(); // Llamada al método renombrado
        },
        error: (error) => {
          console.error('Error al crear:', error);
          this.message = 'Error al crear la EPS.';
          this.loading = false;
        }
      });
    }
  }

  // ----------------------------------------------------
  // --- 3. Eliminar (Delete) ---------------------------
  // ----------------------------------------------------

  /**
   * Elimina una EPS por su ID.
   * @param id El ID de la EPS a eliminar.
   */
  deleteEps(id: number | undefined): void {
    if (id === undefined) return;
    
    const epsName = this.epsList.find(e => e.idEps === id)?.nombreEps;
    const confirmation = confirm(`¿Estás seguro de que quieres eliminar la EPS: ${epsName}?`);

    if (confirmation) {
      this.loading = true;
      this.clearMessages();
      
      this.epsService.delete(id).subscribe({
        next: () => {
          this.message = `EPS "${epsName}" eliminada correctamente.`;
          this.loadEps(); 
          this.prepareCreate(); // Llamada al método renombrado
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          this.message = 'Error al eliminar la EPS.';
          this.loading = false;
        }
      });
    }
  }

  // ----------------------------------------------------
  // --- Métodos Auxiliares -----------------------------
  // ----------------------------------------------------

  /**
   * FIX 3: Renombrado de 'resetForm' a 'prepareCreate' para coincidir con el HTML.
   * Resetea el formulario a su estado inicial.
   */
  prepareCreate(): void {
    this.isEditing = false;
    this.currentEps = { nombreEps: '' }; // Uso de la propiedad 'currentEps'
    this.loading = false;
    // Se mantiene el último mensaje de éxito o error al cancelar.
    // Si desea limpiar el mensaje, cambie la siguiente línea:
    // this.message = null;
  }
  
  /**
   * Limpia el mensaje de estado.
   */
  private clearMessages(): void {
    this.message = null;
  }
}