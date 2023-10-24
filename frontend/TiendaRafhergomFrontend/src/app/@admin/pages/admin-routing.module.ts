import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';

const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      { path: '', loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule) },
      { path: 'usuarios', loadChildren: () => import('@ui/usuario/usuario.module').then(m => m.UsuarioModule) },
      { path: 'productos', loadChildren: () => import('@ui/producto/producto.module').then(m => m.ProductoModule) },
      // { path: '**', redirectTo: 'admin', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
