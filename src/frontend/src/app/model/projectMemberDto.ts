export class ProjectMemberDto {
  public projectKey: string;
  public username: string;
  public role: string;

  constructor(private _projectKey: string, private _username: string, private _role: string) {
    this.projectKey = _projectKey;
    this._username = _username;
    this.role = _role;
  }
}
