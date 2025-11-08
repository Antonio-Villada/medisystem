import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Necesario para el two-way data binding de los formularios
import { Especialidad } from '../../models/especialidad.interface'; // Importamos la interfaz Especialidad
import { EspecialidadService } from '../../service/especialidad.service'; // Importamos el servicio EspecialidadService

@Component({
  selector: 'app-especialidades',
  // Asegúrate de incluir FormsModule si vas a usar formularios en el HTML
  imports: [CommonModule, FormsModule], 
  standalone: true, // Asumo que es standalone
  templateUrl: './especialidades.html',
  styleUrl: './especialidades.css'
})
export class Especialidades implements OnInit { 

  // --- Propiedades para el CRUD ---

  // Leer (Read)
  public especialidades: Especialidad[] = [];
  public loading: boolean = true;
  public errorMessage: string | null = null;
  public successMessage: string | null = null;

  // Crear/Editar (Create/Update)
  // Objeto para el formulario (usado tanto para crear como para editar)
  public especialidadForm: Especialidad = { nombreEspecialidad: '' };
  public isEditing: boolean = false; // Bandera para saber si estamos editando

  // --- Constructor e Inicialización ---

  constructor(private especialidadService: EspecialidadService) { }

  ngOnInit(): void {
    this.loadEspecialidades();
  }

  // --- 1. Leer (Read) - (Obtener todas) ---

  loadEspecialidades(): void {
    this.loading = true;
    this.errorMessage = null;
    this.successMessage = null;
    
    // Llama al servicio para obtener todas las especialidades
    this.especialidadService.getAll().subscribe({
      next: (data) => {
        this.especialidades = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar las especialidades:', error);
        this.errorMessage = 'No se pudieron cargar las especialidades.';
        this.loading = false;
      }
    });
  }
  
  // --- 2. Crear y Actualizar (Create & Update) ---

  // Establece el formulario para crear una nueva especialidad
  openCreateForm(): void {
    this.isEditing = false;
    this.especialidadForm = { nombreEspecialidad: '' }; // Limpia el formulario
  }

  // Establece el formulario para editar una especialidad existente
  editEspecialidad(especialidad: Especialidad): void {
    this.isEditing = true;
    // Crea una copia profunda del objeto para evitar modificar la lista directamente
    this.especialidadForm = { ...especialidad };
  }

  // Lógica para guardar (Crear o Actualizar)
  saveEspecialidad(): void {
    // Validar que el campo no esté vacío
    if (!this.especialidadForm.nombreEspecialidad.trim()) {
      this.errorMessage = 'El nombre de la especialidad no puede estar vacío.';
      return;
    }

    if (this.isEditing && this.especialidadForm.idEspecialidad) {
      // Actualizar una Especialidad existente
      this.especialidadService.update(this.especialidadForm.idEspecialidad, this.especialidadForm).subscribe({
        next: (updatedEspecialidad) => {
          this.successMessage = `Especialidad "${updatedEspecialidad.nombreEspecialidad}" actualizada correctamente.`;
          this.loadEspecialidades(); // Recarga la lista
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al actualizar:', error);
          this.errorMessage = 'Error al actualizar la especialidad.';
        }
      });
    } else {
      // Crear una nueva Especialidad
      // No envía idEspecialidad ya que el backend lo genera automáticamente
      this.especialidadService.create(this.especialidadForm).subscribe({
        next: (newEspecialidad) => {
          this.successMessage = `Especialidad "${newEspecialidad.nombreEspecialidad}" creada correctamente con ID: ${newEspecialidad.idEspecialidad}.`;
          this.loadEspecialidades(); // Recarga la lista
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al crear:', error);
          this.errorMessage = 'Error al crear la especialidad.';
        }
      });
    }
  }

  // --- 3. Eliminar (Delete) ---

  deleteEspecialidad(id: number | undefined): void {
    if (id === undefined) {
      this.errorMessage = 'ID de especialidad no encontrado para eliminar.';
      return;
    }
    
    const especialidadToDelete = this.especialidades.find(e => e.idEspecialidad === id);
    const confirmation = confirm(`¿Estás seguro de que quieres eliminar la especialidad: ${especialidadToDelete?.nombreEspecialidad}?`);

    if (confirmation) {
      // Llama al método delete del servicio
      this.especialidadService.delete(id).subscribe({
        next: () => {
          this.successMessage = `Especialidad eliminada correctamente.`;
          this.loadEspecialidades(); // Recarga la lista para reflejar el cambio
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          this.errorMessage = 'Error al eliminar la especialidad.';
        }
      });
    }
  }

  // --- Métodos Auxiliares ---

  resetForm(): void {
    this.isEditing = false;
    this.especialidadForm = { nombreEspecialidad: '' };
    this.errorMessage = null; // Limpia errores después de una operación exitosa
  }
}