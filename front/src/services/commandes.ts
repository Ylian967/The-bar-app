import http from './http'
import type { Commande, CommandeRequest } from '../types/models'

/** Appels API liés aux commandes. */
export const commandeService = {
  passer(commande: CommandeRequest) {
    return http.post<Commande>('/api/commandes', commande).then((r) => r.data)
  },
  trouver(id: number) {
    return http.get<Commande>(`/api/commandes/${id}`).then((r) => r.data)
  },
  mesCommandes(clientNom: string) {
    return http.get<Commande[]>('/api/commandes', { params: { clientNom } }).then((r) => r.data)
  },
  aTraiter() {
    return http.get<Commande[]>('/api/commandes/a-traiter').then((r) => r.data)
  },
  avancerLigne(commandeId: number, ligneId: number) {
    return http
      .patch<Commande>(`/api/commandes/${commandeId}/lignes/${ligneId}/avancer`)
      .then((r) => r.data)
  },
}
