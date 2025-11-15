import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { Formula, DetalleFormula } from '../models/formula.model';
import { FormulaService } from '../services/formula.service';

@Component({
  standalone: true,
  imports: [CommonModule],
  selector: 'app-formula-list',
  templateUrl: './formula-list.component.html',
  styleUrls: ['./formula-list.component.css'],
})
export class FormulaListComponent implements OnInit {
  formulas$: Observable<Formula[]>;
  selectedFormula: Formula | null = null;

  constructor(private formulaService: FormulaService) {
    this.formulas$ = new Observable<Formula[]>();
  }

  ngOnInit(): void {
    this.formulas$ = this.formulaService.getMisFormulas();
  }

  // Método para mostrar el detalle en un modal
  verDetalle(formula: Formula): void {
    this.selectedFormula = formula;
  }

  // Método para la navegación a la edición
  editarFormula(id: number): void {
    console.log('Navegar a la edición de la fórmula', id);
    // this.router.navigate(['/formulas/editar', id]);
  }

  cerrarModal(): void {
    this.selectedFormula = null;
  }
}
