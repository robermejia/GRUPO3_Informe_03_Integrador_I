import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderToolbar } from '../header-toolbar/header-toolbar';

@Component({
  selector: 'app-layout',
  imports: [HeaderToolbar, RouterOutlet],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout {}
