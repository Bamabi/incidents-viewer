/**
 * Incident model interface
 */
export interface Incident {
  id: string;
  title: string;
  description: string;
  severity: string;
  ownerLastName: string;
  ownerFirstName: string;
  ownerEmail: string;
  createdAt: string;
  updatedAt: string;
}
