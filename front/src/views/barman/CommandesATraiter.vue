<script setup lang="ts">
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { commandeService } from '../../services/commandes'
import { useAuthStore } from '../../stores/auth'
import type { Commande, StatutCommande } from '../../types/models'
import SidebarBarman from '../../components/barman/SidebarBarman.vue'

const router = useRouter()
const auth = useAuthStore()

const commandes = ref<Commande[]>([])
const terminees = ref<Commande[]>([])
const filtre = ref<'COMMANDEE' | 'EN_PREPARATION' | 'TERMINEE'>('COMMANDEE')
let timer: number | undefined

const affichees = computed(() =>
  filtre.value === 'TERMINEE' ? terminees.value : commandes.value.filter((c) => c.statut === filtre.value),
)

function badge(s: StatutCommande): string {
  return s === 'TERMINEE' ? 'done' : s === 'EN_PREPARATION' ? 'prep' : 'cmd'
}
function libelle(s: StatutCommande): string {
  return s === 'TERMINEE' ? 'Terminée' : s === 'EN_PREPARATION' ? 'En prépa' : 'Commandée'
}
function heure(iso: string): string {
  return new Date(iso).toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })
}
function nbCocktails(c: Commande): number {
  return c.lignes.reduce((s, l) => s + l.quantite, 0)
}

async function charger() {
  ;[commandes.value, terminees.value] = await Promise.all([
    commandeService.aTraiter(),
    commandeService.terminees(),
  ])
}

function deconnexion() {
  auth.logout()
  router.push({ name: 'login' })
}

onMounted(() => {
  charger()
  timer = window.setInterval(charger, 5000) // rafraîchissement live
})
onUnmounted(() => clearInterval(timer))
</script>

<template>
  <div class="layout">
    <SidebarBarman />
    <main class="main">
      <header class="tbar">
        <div>
          <h2>Commandes à traiter</h2>
          <p class="sub">{{ commandes.length }} commande(s) en attente</p>
        </div>
        <div class="who">
          <span class="avt">{{ (auth.nom ?? 'B')[0] }}</span>
          Barmaker · {{ auth.nom }}
          <button class="out" @click="deconnexion">Déconnexion</button>
        </div>
      </header>

      <div class="tabs">
        <button class="t" :class="{ on: filtre === 'COMMANDEE' }" @click="filtre = 'COMMANDEE'">À faire</button>
        <button class="t" :class="{ on: filtre === 'EN_PREPARATION' }" @click="filtre = 'EN_PREPARATION'">En cours</button>
        <button class="t" :class="{ on: filtre === 'TERMINEE' }" @click="filtre = 'TERMINEE'">Terminées</button>
      </div>

      <p v-if="affichees.length === 0" class="etat">Aucune commande dans cet onglet.</p>

      <div class="grid">
        <article v-for="c in affichees" :key="c.id" class="tcard" @click="router.push({ name: 'detail-commande', params: { id: c.id } })">
          <div class="top">
            <span class="id">#{{ c.id }}</span>
            <span class="badge" :class="badge(c.statut)">{{ libelle(c.statut) }}</span>
          </div>
          <div class="li">
            <svg class="mi" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><circle cx="12" cy="12" r="9" /><path d="M12 7v5l3 2" /></svg>
            {{ heure(c.dateCreation) }} · {{ nbCocktails(c) }} cocktail(s)
          </div>
          <div class="li noms">{{ c.lignes.map((l) => l.cocktailNom).join(' · ') }}</div>
          <div class="li client">
            <svg class="mi" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="8" r="3.4" /><path d="M5 20c1.4-3.6 4-5.2 7-5.2s5.6 1.6 7 5.2" /></svg>
            {{ c.clientNom }}
          </div>
          <div class="go">Traiter →</div>
        </article>
      </div>
    </main>
  </div>
</template>

<style scoped>
.layout { display: flex; min-height: 100vh; background: var(--cream); }
.main { flex: 1; padding: 26px 30px; }
.tbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.tbar h2 { font-size: 24px; font-weight: 800; }
.sub { color: var(--ink-soft); font-size: 13px; }
.who { display: flex; align-items: center; gap: 10px; font-size: 13px; font-weight: 600; }
.avt { width: 38px; height: 38px; border-radius: 50%; background: var(--accent); display: grid; place-items: center; color: #fff; font-weight: 700; }
.out { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border: 1px solid var(--line); border-radius: 12px; padding: 8px 12px; color: var(--ink-soft); font-size: 12px; font-weight: 600; }
.tabs { display: flex; gap: 8px; background: rgba(255,255,255,0.08); padding: 5px; border-radius: 16px; max-width: 360px; margin-bottom: 20px; }
.t { flex: 1; padding: 10px; border-radius: 12px; font-weight: 600; font-size: 13px; color: var(--ink-soft); background: transparent; }
.t.on { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); color: var(--ink); box-shadow: var(--shadow-sm); }
.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(230px, 1fr)); gap: 16px; }
.tcard { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); border-radius: 20px; padding: 18px; box-shadow: var(--shadow-sm); cursor: pointer; transition: transform 0.1s; }
.tcard:hover { transform: translateY(-2px); }
.top { display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.id { font-family: 'Sora', sans-serif; font-weight: 700; font-size: 18px; }
.li { display: flex; align-items: center; gap: 6px; font-size: 13px; color: var(--ink-soft); margin: 3px 0; }
.li.noms { color: var(--ink); }
.mi { width: 14px; height: 14px; flex-shrink: 0; }
.go { margin-top: 14px; background: var(--btn); border: 1px solid var(--btn-stroke); backdrop-filter: blur(16px) saturate(150%); -webkit-backdrop-filter: blur(16px) saturate(150%); color: #fff; text-align: center; padding: 11px; border-radius: 14px; font-family: 'Sora', sans-serif; font-weight: 700; font-size: 14px; box-shadow: var(--btn-sheen); }

@media (max-width: 640px) {
  .main { padding: 16px 14px; }
  .tbar { flex-wrap: wrap; gap: 12px; }
  .tbar h2 { font-size: 20px; }
  .who { font-size: 12px; gap: 8px; }
  .tabs { max-width: none; }
  .grid { grid-template-columns: repeat(auto-fill, minmax(160px, 1fr)); }
}
</style>
