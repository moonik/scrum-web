export class ProjectMemberDto {
  username: string;
  role: string;

  constructor(private username: string, private role: string) {
    this.username = username;
    this.role = role;
  }
}
