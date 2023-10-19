import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-title',
  templateUrl: './title.component.html',
  styles: [
    `
      .t-h{
        color: #181f2c !important;
      }
    `
  ]
})
export class TitleComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
