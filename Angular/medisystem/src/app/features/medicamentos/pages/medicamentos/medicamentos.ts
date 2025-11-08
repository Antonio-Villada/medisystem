import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common'; // Para directivas como *ngIf y *ngFor
import { FormsModule } from '@angular/forms'; // Para two-way data binding [(ngModel)]

import { Medicamento } from '../../models/medicamento.interface'; // Importamos la interfaz
import { MedicamentoService } from '../../service/medicamento.service'; // Importamos el servicio

@Component({
  selector: 'app-medicamentos',
  standalone: true, // Asumimos que es un componente standalone
  imports: [CommonModule, FormsModule], 
  templateUrl: './medicamentos.html',
  styleUrl: './medicamentos.css'
})
export class Medicamentos implements OnInit {

  // 1. Inyección de Dependencias
  private medicamentoService = inject(MedicamentoService); // Usamos inject() para el servicio

  // --- Propiedades para el CRUD ---

  // Leer (Read)
  public medicamentoList: Medicamento[] = [];
  public loading: boolean = true;

  // Crear/Editar (Create/Update)
  // Objeto para el formulario
  public medicamentoForm: Medicamento = { nombreMedicamento: '', precio: 0 }; // Inicialización con propiedades
  public isEditing: boolean = false; // Bandera para saber si estamos editando

  // Mensajes de estado
  public errorMessage: string | null = null;
  public successMessage: string | null = null;

  // --- Inicialización ---

  ngOnInit(): void {
    this.loadMedicamentos();
  }

  // --- 1. Leer (Read) - Obtener todos ---

  loadMedicamentos(): void {
    this.loading = true;
    this.errorMessage = null;
    this.successMessage = null;
    
    // Llama al servicio para obtener todos los medicamentos
    this.medicamentoService.getAll().subscribe({
      next: (data) => {
        this.medicamentoList = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar medicamentos:', error);
        this.errorMessage = 'No se pudieron cargar los medicamentos. Inténtelo de nuevo.';
        this.loading = false;
      }
    });
  }
  
  // --- 2. Crear y Actualizar (Create & Update) ---

  // Prepara el formulario para editar un medicamento
  editMedicamento(medicamento: Medicamento): void {
    this.isEditing = true;
    // Creamos una copia del objeto para editar
    this.medicamentoForm = { ...medicamento }; 
  }

  // Lógica para guardar (Crear o Actualizar)
  saveMedicamento(): void {
    // Validación básica
    if (!this.medicamentoForm.nombreMedicamento.trim() || this.medicamentoForm.precio <= 0) {
      this.errorMessage = 'El nombre y el precio deben ser válidos.';
      return;
    }

    if (this.isEditing && this.medicamentoForm.idMedicamento) {
      // Actualizar (Update)
      this.medicamentoService.update(this.medicamentoForm.idMedicamento, this.medicamentoForm).subscribe({
        next: (updatedMedicamento) => {
          this.successMessage = `Medicamento "${updatedMedicamento.nombreMedicamento}" actualizado correctamente.`;
          this.loadMedicamentos(); // Recarga la lista
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al actualizar:', error);
          this.errorMessage = 'Error al actualizar el medicamento.';
        }
      });
    } else {
      // Crear (Create)
      // Creamos un objeto sin el idMedicamento ya que el backend lo genera
      const newMedicamento: Medicamento = {
        nombreMedicamento: this.medicamentoForm.nombreMedicamento,
        precio: this.medicamentoForm.precio
      };

      this.medicamentoService.create(newMedicamento).subscribe({
        next: (createdMedicamento) => {
          this.successMessage = `Medicamento "${createdMedicamento.nombreMedicamento}" creado correctamente.`;
          this.loadMedicamentos(); // Recarga la lista
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al crear:', error);
          this.errorMessage = 'Error al crear el medicamento.';
        }
      });
    }
  }

  // --- 3. Eliminar (Delete) ---

  deleteMedicamento(id: number | undefined): void {
    if (id === undefined) return;
    
    const medicamentoName = this.medicamentoList.find(m => m.idMedicamento === id)?.nombreMedicamento;
    const confirmation = confirm(`¿Estás seguro de que quieres eliminar el medicamento: ${medicamentoName}?`);

    if (confirmation) {
      // Llama al método delete del servicio
      this.medicamentoService.delete(id).subscribe({
        next: () => {
          this.successMessage = `Medicamento "${medicamentoName}" eliminado correctamente.`;
          this.loadMedicamentos(); // Recarga la lista
          this.resetForm();
        },
        error: (error) => {
          console.error('Error al eliminar:', error);
          this.errorMessage = 'Error al eliminar el medicamento.';
        }
      });
    }
  }

  // --- Métodos Auxiliares ---

  resetForm(): void {
    this.isEditing = false;
    this.medicamentoForm = { nombreMedicamento: '', precio: 0 };
    // Mantenemos el successMessage visible un momento, pero limpiamos el errorMessage
    this.errorMessage = null; 
  }
}