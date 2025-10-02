import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { About } from './pages/about/about';
import { Products } from './pages/products/products';
import { Contact } from './pages/contact/contact';

export const routes: Routes = [
  {path:'', component:Home},
  {path:'nosotros', component:About},
  {path: 'produsto',component:Products},
  {path:'contacto', component:Contact},
  {path: '**', redirectTo: ''}

];
