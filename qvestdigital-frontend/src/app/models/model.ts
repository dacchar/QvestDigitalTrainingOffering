export type ICourse = {
  id: number;
  name: string;
  description: string;
  instructor: string;
  price: number;
};

export class Course {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public instructor: string,
    public price: Number,
  ){
  }
};
