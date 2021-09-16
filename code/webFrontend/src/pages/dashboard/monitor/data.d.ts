export type TagType = {
  name: string;
  value: number;
  type: string;
};

export type JobType = {
  uuid: string;
  scheduleStatus: string;
  scheduleType: string;
  cron: string;
  cronWord?: string;
};

export type SubmitJobType = {
  cronStrings: [string];
  scheduleType: number;
  arguments: any;
};
