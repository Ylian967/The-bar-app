<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { commandeService } from '../../services/commandes'
import type { Commande, StatutCommande } from '../../types/models'
import BottomNav from '../../components/BottomNav.vue'

const clientNom = localStorage.getItem('clientNom') ?? ''
const commandes = ref<Commande[]>([])
const onglet = ref<'cours' | 'historique'>('cours')
const chargement = ref(false)

const enCours = computed(() => commandes.value.filter((c) => c.statut !== 'TERMINEE'))
const historique = computed(() => commandes.value.filter((c) => c.statut === 'TERMINEE'))

const ETAPES = ['Commandée', 'En préparation', 'Terminée']
function indexEtape(statut: StatutCommande): number {
  return statut === 'TERMINEE' ? 2 : statut === 'EN_PREPARATION' ? 1 : 0
}
function badge(statut: StatutCommande): string {
  return statut === 'TERMINEE' ? 'done' : statut === 'EN_PREPARATION' ? 'prep' : 'cmd'
}
function resume(c: Commande): string {
  return c.lignes.map((l) => `${l.cocktailNom} (${l.taille})×${l.quantite}`).join(' · ')
}
function quand(iso: string): string {
  return new Date(iso).toLocaleString('fr-FR', { day: '2-digit', month: 'short', hour: '2-digit', minute: '2-digit' })
}

async function charger() {
  if (!clientNom) return
  chargement.value = true
  commandes.value = await commandeService.mesCommandes(clientNom)
  chargement.value = false
}

onMounted(charger)
</script>

<template>
  <div class="screen">
    <div class="haut">
      <h1>Mes commandes</h1>
      <button v-if="clientNom" class="refresh" @click="charger">↻</button>
    </div>

    <p v-if="!clientNom" class="etat">Passez une commande pour suivre sa préparation ici.</p>

    <template v-else>
      <div class="tabs">
        <button class="t" :class="{ on: onglet === 'cours' }" @click="onglet = 'cours'">En cours</button>
        <button class="t" :class="{ on: onglet === 'historique' }" @click="onglet = 'historique'">Historique</button>
      </div>

      <p v-if="chargement" class="etat">Chargement…</p>
      <template v-else>
        <div v-for="c in onglet === 'cours' ? enCours : historique" :key="c.id" class="card cmd">
          <div class="top">
            <span class="id">Commande #{{ c.id }}</span>
            <span class="badge" :class="badge(c.statut)">{{ ETAPES[indexEtape(c.statut)] }}</span>
          </div>
          <div class="date">{{ quand(c.dateCreation) }}</div>
          <div class="contenu">{{ resume(c) }} — <b>{{ c.total.toFixed(2) }} €</b></div>
          <div class="steps">
            <template v-for="(etape, i) in ETAPES" :key="etape">
              <div class="dot" :class="{ done: i < indexEtape(c.statut) || c.statut === 'TERMINEE', now: i === indexEtape(c.statut) && c.statut !== 'TERMINEE' }"></div>
              <div v-if="i < 2" class="bar" :class="{ fill: i < indexEtape(c.statut) || c.statut === 'TERMINEE' }"></div>
            </template>
          </div>
          <div class="steplabels">
            <span v-for="etape in ETAPES" :key="etape">{{ etape }}</span>
          </div>
        </div>
        <p v-if="(onglet === 'cours' ? enCours : historique).length === 0" class="etat">Aucune commande ici.</p>
      </template>
    </template>

    <BottomNav />
  </div>
</template>

<style scoped>
.screen { max-width: 480px; margin: 0 auto; min-height: 100vh; background: var(--cream); padding: 22px 18px 100px; }
.haut { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; }
h1 { font-size: 28px; font-weight: 800; }
.refresh { width: 40px; height: 40px; border-radius: 50%; background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); box-shadow: var(--shadow-sm); font-size: 18px; }
.tabs { display: flex; gap: 8px; background: rgba(255,255,255,0.08); padding: 5px; border-radius: 16px; margin-bottom: 16px; }
.t { flex: 1; padding: 10px; border-radius: 12px; font-weight: 600; font-size: 13px; color: var(--ink-soft); background: transparent; }
.t.on { background: var(--glass-strong); border: 1px solid var(--stroke); backdrop-filter: blur(20px) saturate(150%); color: var(--ink); box-shadow: var(--shadow-sm); }
.card.cmd { padding: 16px; margin-bottom: 14px; }
.top { display: flex; justify-content: space-between; align-items: center; }
.id { font-family: 'Sora', sans-serif; font-weight: 700; font-size: 16px; }
.date { color: var(--ink-soft); font-size: 12px; margin-top: 2px; }
.contenu { font-size: 13px; margin: 8px 0 14px; }
.steps { display: flex; align-items: center; }
.dot { width: 22px; height: 22px; border-radius: 50%; background: rgba(255,255,255,0.08); flex-shrink: 0; }
.dot.done { background: var(--teal); }
.dot.now { background: var(--coral); box-shadow: 0 0 0 4px rgba(255, 77, 109, 0.18); }
.bar { flex: 1; height: 3px; background: rgba(255,255,255,0.08); margin: 0 4px; }
.bar.fill { background: var(--teal); }
.steplabels { display: flex; justify-content: space-between; margin-top: 7px; font-size: 10px; color: var(--ink-soft); font-weight: 600; }
</style>
