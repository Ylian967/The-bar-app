<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { commandeService } from '../../services/commandes'
import { imageUrl } from '../../services/http'
import type { Commande, StatutPreparation } from '../../types/models'
import SidebarBarman from '../../components/barman/SidebarBarman.vue'

const route = useRoute()
const router = useRouter()
const commandeId = Number(route.params.id)

const commande = ref<Commande | null>(null)

const ETAPES: StatutPreparation[] = ['PREPARATION_INGREDIENTS', 'ASSEMBLAGE', 'DRESSAGE', 'TERMINEE']
const LIBELLES = ['Ingrédients', 'Assemblage', 'Dressage', 'Terminée']

function idxEtape(s: StatutPreparation): number {
  return ETAPES.indexOf(s)
}
function prochaine(s: StatutPreparation): string {
  return s === 'TERMINEE' ? '' : LIBELLES[idxEtape(s) + 1]
}
const toutTermine = computed(
  () => !!commande.value && commande.value.lignes.every((l) => l.statutPreparation === 'TERMINEE'),
)

async function charger() {
  commande.value = await commandeService.trouver(commandeId)
}
async function avancer(ligneId: number) {
  commande.value = await commandeService.avancerLigne(commandeId, ligneId)
}

onMounted(charger)
</script>

<template>
  <div class="layout">
    <SidebarBarman />
    <main v-if="commande" class="main">
      <header class="tbar">
        <div class="gauche">
          <button class="back" @click="router.push({ name: 'commandes-a-traiter' })">‹</button>
          <div>
            <h2>Commande #{{ commande.id }}</h2>
            <p class="sub">👤 {{ commande.clientNom }}</p>
          </div>
        </div>
        <span class="badge" :class="commande.statut === 'TERMINEE' ? 'done' : commande.statut === 'EN_PREPARATION' ? 'prep' : 'cmd'">
          {{ commande.statut === 'TERMINEE' ? 'Terminée' : commande.statut === 'EN_PREPARATION' ? 'En préparation' : 'Commandée' }}
        </span>
      </header>

      <article v-for="l in commande.lignes" :key="l.id" class="pcard">
        <div class="ph">
          <div class="nm">
            <img :src="imageUrl('/images/cocktails/' + l.cocktailNom.toLowerCase().replace(/[^a-z0-9]+/g, '_') + '.jpg')" :alt="l.cocktailNom" />
            {{ l.cocktailNom }} <small>· Taille {{ l.taille }} · ×{{ l.quantite }}</small>
          </div>
          <span class="badge" :class="l.statutPreparation === 'TERMINEE' ? 'done' : 'prep'">{{ LIBELLES[idxEtape(l.statutPreparation)] }}</span>
        </div>

        <div class="psteps">
          <template v-for="(lib, i) in LIBELLES" :key="lib">
            <div class="s" :class="{ done: i < idxEtape(l.statutPreparation) || l.statutPreparation === 'TERMINEE', now: i === idxEtape(l.statutPreparation) && l.statutPreparation !== 'TERMINEE' }">
              <div class="c">{{ i < idxEtape(l.statutPreparation) || l.statutPreparation === 'TERMINEE' ? '✓' : i + 1 }}</div>
              <div class="lb">{{ lib }}</div>
            </div>
            <div v-if="i < 3" class="ln" :class="{ fill: i < idxEtape(l.statutPreparation) || l.statutPreparation === 'TERMINEE' }"></div>
          </template>
        </div>

        <button v-if="l.statutPreparation !== 'TERMINEE'" class="next" @click="avancer(l.id)">
          Étape suivante → {{ prochaine(l.statutPreparation) }}
        </button>
        <div v-else class="fini">✓ Cocktail prêt</div>
      </article>

      <div v-if="toutTermine" class="banner">✅ Commande terminée — prête à servir !</div>
    </main>
  </div>
</template>

<style scoped>
.layout { display: flex; min-height: 100vh; background: var(--cream); }
.main { flex: 1; padding: 26px 30px; max-width: 760px; }
.tbar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.gauche { display: flex; align-items: center; gap: 12px; }
.back { width: 40px; height: 40px; border-radius: 12px; background: #fff; border: 1px solid var(--line); font-size: 20px; }
.tbar h2 { font-size: 22px; font-weight: 800; }
.sub { color: var(--ink-soft); font-size: 13px; }
.pcard { background: #fff; border-radius: 20px; padding: 16px 18px; box-shadow: var(--shadow-sm); margin-bottom: 16px; }
.ph { display: flex; justify-content: space-between; align-items: center; }
.nm { display: flex; align-items: center; gap: 10px; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 17px; }
.nm img { width: 34px; height: 34px; border-radius: 10px; object-fit: cover; }
.nm small { font-family: 'Inter', sans-serif; font-weight: 500; color: var(--ink-soft); }
.psteps { display: flex; align-items: center; margin: 16px 0 6px; }
.s { text-align: center; }
.s .c { width: 30px; height: 30px; border-radius: 50%; margin: 0 auto; display: grid; place-items: center; font-size: 13px; font-weight: 700; background: #efe9f5; color: #b6adc6; }
.s.done .c { background: var(--teal); color: #fff; }
.s.now .c { background: var(--coral); color: #fff; box-shadow: 0 0 0 5px rgba(255, 77, 109, 0.18); }
.s .lb { font-size: 10px; margin-top: 6px; color: var(--ink-soft); font-weight: 600; }
.ln { flex: 1; height: 3px; background: #efe9f5; margin: 0 4px; margin-top: -18px; }
.ln.fill { background: var(--teal); }
.next { margin-top: 12px; width: 100%; background: var(--ink); color: #fff; padding: 11px; border-radius: 13px; font-family: 'Poppins', sans-serif; font-weight: 600; font-size: 13px; }
.fini { margin-top: 12px; text-align: center; color: var(--teal-d); font-weight: 700; font-size: 13px; }
.banner { display: flex; align-items: center; justify-content: center; gap: 8px; background: linear-gradient(135deg, var(--teal), #5bd86e); color: #fff; text-align: center; padding: 16px; border-radius: 18px; font-family: 'Poppins', sans-serif; font-weight: 700; box-shadow: 0 12px 24px rgba(0, 194, 168, 0.4); }
</style>
