export class ProjectMemberDto {
  projectKey: string;
  username: string;
  role: string;

  constructor(private _projectKey: string, private _username: string, private _role: string) {
    this.projectKey = _projectKey;
    this._username = _username;
    this.role = _role;
  }
}
