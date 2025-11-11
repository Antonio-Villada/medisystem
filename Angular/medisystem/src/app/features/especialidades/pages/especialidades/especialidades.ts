import { Component, OnInit, inject } from '@angular/core'; // Importamos 'inject'
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Especialidad } from '../../models/especialidad.interface';
import { EspecialidadService } from '../../service/especialidad.service'; // Asegúrate de que este servicio use @Injectable()

@Component({
  selector: 'app-especialidades',
  imports: [CommonModule, FormsModule], 
  standalone: true, 
  templateUrl: './especialidades.html',
  styleUrl: './especialidades.css'
})
export class EspecialidadesComponent implements OnInit { 

  // 1. INYECCIÓN DE DEPENDENCIAS usando 'inject()'
  // La variable ahora es privada y se inicializa con la función 'inject()'
  private especialidadService = inject(EspecialidadService);

  // --- Propiedades para el CRUD ---

  public especialidades: Especialidad[] = [];
  public loading: boolean = true;
  public errorMessage: string | null = null;
  public successMessage: string | null = null;

  public especialidadForm: Partial<Especialidad> = { nombreEspecialidad: '' };
  public isEditing: boolean = false; 

  // El constructor ahora está vacío o puede eliminarse si no hace nada más.
  // constructor() { } 

  ngOnInit(): void {
    this.loadEspecialidades();
  }

  // -------------------------------------------------------------------
  // --- 1. Leer (Read) ------------------------------------------------
  // -------------------------------------------------------------------

  loadEspecialidades(): void {
    this.loading = true;
    this.errorMessage = null;
    this.successMessage = null;
    
    // Accedemos al servicio a través de 'this.especialidadService'
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
  
  // -------------------------------------------------------------------
  // --- 2. Crear y Actualizar (Create & Update) -----------------------
  // -------------------------------------------------------------------

  openCreateForm(): void {
    this.isEditing = false;
    this.especialidadForm = { nombreEspecialidad: '' };
  }

  editEspecialidad(especialidad: Especialidad): void {
    this.isEditing = true;
    this.especialidadForm = { ...especialidad };
  }

  saveEspecialidad(): void {
    if (!this.especialidadForm.nombreEspecialidad || !this.especialidadForm.nombreEspecialidad.trim()) {
      this.errorMessage = 'El nombre de la especialidad no puede estar vacío.';
      return;
    }
    
    this.loading = true;
    this.errorMessage = null;

    if (this.isEditing && this.especialidadForm.idEspecialidad) {
      // Actualizar
      this.especialidadService.update(this.especialidadForm.idEspecialidad, this.especialidadForm as Especialidad).subscribe({
        next: (updatedEspecialidad) => {
          this.successMessage = `Especialidad "${updatedEspecialidad.nombreEspecialidad}" actualizada correctamente.`;
          this.loadEspecialidades(); 
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al actualizar:', error);
          this.errorMessage = 'Error al actualizar la especialidad.';
          this.loading = false;
        }
      });
    } else {
      // Crear
      const newEspecialidad: Especialidad = { 
        nombreEspecialidad: this.especialidadForm.nombreEspecialidad! // Usamos '!' ya validamos que no es null
      };

      this.especialidadService.create(newEspecialidad).subscribe({
        next: (createdEspecialidad) => {
          this.successMessage = `Especialidad "${createdEspecialidad.nombreEspecialidad}" creada correctamente.`;
          this.loadEspecialidades(); 
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al crear:', error);
          this.errorMessage = 'Error al crear la especialidad.';
          this.loading = false;
        }
      });
    }
  }

  // -------------------------------------------------------------------
  // --- 3. Eliminar (Delete) ------------------------------------------
  // -------------------------------------------------------------------

  deleteEspecialidad(id: number | undefined): void {
    if (id === undefined) {
      this.errorMessage = 'ID de especialidad no encontrado para eliminar.';
      return;
    }
    
    const especialidadToDelete = this.especialidades.find(e => e.idEspecialidad === id);
    const confirmation = confirm(`¿Estás seguro de que quieres eliminar la especialidad: ${especialidadToDelete?.nombreEspecialidad}?`);

    if (confirmation) {
      this.loading = true;
      this.errorMessage = null;
      
      this.especialidadService.delete(id).subscribe({
        next: () => {
          this.successMessage = `Especialidad "${especialidadToDelete?.nombreEspecialidad}" eliminada correctamente.`;
          this.loadEspecialidades(); 
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          this.errorMessage = 'Error al eliminar la especialidad.';
          this.loading = false;
        }
      });
    }
  }

  // --- Métodos Auxiliares ---

  resetForm(): void {
    this.isEditing = false;
    this.especialidadForm = { nombreEspecialidad: '' };
    this.errorMessage = null; 
    this.loading = false;
  }
}