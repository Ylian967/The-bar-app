<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { commandeService } from '../../services/commandes'
import { imageUrl } from '../../services/http'
import { usePanierStore } from '../../stores/panier'
import type { CommandeRequest } from '../../types/models'
import BottomNav from '../../components/BottomNav.vue'

const router = useRouter()
const panier = usePanierStore()

const clientNom = ref(localStorage.getItem('clientNom') ?? '')
const envoiEnCours = ref(false)
const erreur = ref('')

async function commander() {
  if (!clientNom.value.trim() || panier.articles.length === 0) return
  envoiEnCours.value = true
  erreur.value = ''
  try {
    const requete: CommandeRequest = {
      clientNom: clientNom.value.trim(),
      lignes: panier.articles.map((a) => ({
        cocktailId: a.cocktailId,
        taille: a.taille,
        quantite: a.quantite,
      })),
    }
    await commandeService.passer(requete)
    localStorage.setItem('clientNom', requete.clientNom)
    panier.vider()
    router.push({ name: 'mes-commandes' })
  } catch {
    erreur.value = "Impossible d'envoyer la commande. Réessayez."
  } finally {
    envoiEnCours.value = false
  }
}
</script>

<template>
  <div class="screen">
    <h1>Mon panier</h1>

    <p v-if="panier.articles.length === 0" class="etat">
      Votre panier est vide.<br />Ajoutez des cocktails depuis la carte. 🍹
    </p>

    <template v-else>
      <div class="liste">
        <div v-for="(a, i) in panier.articles" :key="i" class="item">
          <img :src="imageUrl(a.imageUrl)" :alt="a.nom" />
          <div class="meta">
            <div class="nom">{{ a.nom }}</div>
            <div class="taille">Taille {{ a.taille }}</div>
          </div>
          <div class="qty">
            <button @click="panier.changerQuantite(i, -1)">−</button>
            <span>{{ a.quantite }}</span>
            <button @click="panier.changerQuantite(i, 1)">+</button>
          </div>
          <div class="prix">{{ (a.prix * a.quantite).toFixed(2) }} €</div>
        </div>
      </div>

      <label class="champ">
        Votre prénom / table
        <input v-model="clientNom" type="text" placeholder="Ex : Ylian — Table 4" />
      </label>

      <div class="recap">
        <div class="ligne total"><span>Total</span><span>{{ panier.total.toFixed(2) }} €</span></div>
      </div>

      <p v-if="erreur" class="erreur">{{ erreur }}</p>
      <button class="btn" :disabled="!clientNom.trim() || envoiEnCours" @click="commander">
        {{ envoiEnCours ? 'Envoi…' : 'Commander' }}
      </button>
    </template>

    <BottomNav />
  </div>
</template>

<style scoped>
.screen { max-width: 480px; margin: 0 auto; min-height: 100vh; background: var(--cream); padding: 22px 18px 100px; }
h1 { font-size: 28px; font-weight: 800; margin-bottom: 16px; }
.liste { display: flex; flex-direction: column; gap: 11px; }
.item { display: flex; align-items: center; gap: 12px; background: #fff; border-radius: 20px; padding: 12px; box-shadow: var(--shadow-sm); }
.item img { width: 52px; height: 52px; border-radius: 14px; object-fit: cover; }
.meta { flex: 1; }
.meta .nom { font-family: 'Poppins', sans-serif; font-weight: 600; font-size: 15px; }
.meta .taille { color: var(--ink-soft); font-size: 12px; }
.qty { display: flex; align-items: center; gap: 8px; font-family: 'Poppins', sans-serif; font-weight: 700; font-size: 13px; }
.qty button { width: 26px; height: 26px; border-radius: 8px; background: #f2ecf7; color: var(--ink); font-size: 16px; }
.prix { font-family: 'Poppins', sans-serif; font-weight: 700; color: var(--coral); font-size: 14px; min-width: 56px; text-align: right; }
.champ { display: block; margin-top: 18px; font-size: 12px; font-weight: 600; color: var(--ink-soft); }
.champ input { display: block; width: 100%; margin-top: 6px; background: #fff; border: 1.5px solid var(--line); border-radius: 14px; padding: 13px 14px; font-size: 14px; font-family: inherit; }
.recap { background: #fff; border-radius: 20px; padding: 16px 18px; box-shadow: var(--shadow-sm); margin-top: 16px; }
.ligne { display: flex; justify-content: space-between; }
.ligne.total { font-family: 'Poppins', sans-serif; font-weight: 800; font-size: 20px; }
.erreur { color: var(--coral-d); font-size: 13px; margin: 12px 0; text-align: center; }
.btn { margin-top: 18px; }
</style>
