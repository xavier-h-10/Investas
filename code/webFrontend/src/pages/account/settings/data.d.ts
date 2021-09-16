export type TagType = {
  key: string;
  label: string;
};

export type GeographicItemType = {
  name: string;
  id: string;
};

export type GeographicType = {
  province: GeographicItemType;
  city: GeographicItemType;
};

export type Role = {
  roleId: number;
  roleNameZH: string;
  roleNameEN: string;
};

export type CurrentUser = {
  userId: number;
  nickname: string;
  email: string;
  status: number;
  avatarUrl: string;
  username: string;
  riskLevel: number;
  riskLevelName: string
  account_non_expired: string;
  statusName: string;
  roleName: string;
  introduction: string;
  roles: Role [];
};
